package com.example.wallet.service;

import com.example.wallet.dto.IncomingDataDto;
import com.example.wallet.dto.WalletDto;
import com.example.wallet.exception.IncorrectDataException;
import com.example.wallet.exception.NotFoundException;
import com.example.wallet.mapper.WalletMapper;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.example.wallet.enums.OperationType.DEPOSIT;
import static com.example.wallet.enums.OperationType.WITHDRAW;
import static java.math.BigDecimal.ZERO;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final WalletMapper mapper;

    @Override
    @Transactional
    public WalletDto makeADeposit(IncomingDataDto incomingData) {
        log.info("Данные загружены: {}", incomingData);
        Wallet wallet = findWallet(incomingData.getWalletId());
        if (DEPOSIT.equals(incomingData.getOperationType())) {
            wallet.setAmount(incomingData.getAmount().add(new BigDecimal(wallet.getAmount())).toPlainString());
            log.info("Кошелек пополнен: {}", wallet);
        }
        if (WITHDRAW.equals(incomingData.getOperationType())) {
            if (new BigDecimal(wallet.getAmount()).subtract(incomingData.getAmount()).compareTo(ZERO) < 0) {
                throw new IncorrectDataException("На счету не достаточно средств");
            } else {
                wallet.setAmount(incomingData.getAmount().subtract(new BigDecimal(wallet.getAmount())).toPlainString());
                log.info("Вывести с кошелька: {}", wallet);
            }
        }
        return mapper.toWalletDto(wallet);
    }

    @Override
    public WalletDto getData(UUID walletId) {
        Wallet wallet = findWallet(walletId);
        log.info("Загрузка данных: {}", wallet);
        return mapper.toWalletDto(wallet);
    }

    private Wallet findWallet(UUID walletId) {
        Optional<Wallet> wallet = repository.findByWalletId(walletId);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new NotFoundException("Такой кошелек не существует");
        }
    }
}

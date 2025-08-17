package com.valber.financial_control.application.scheduled;

import com.valber.financial_control.application.kafka.messages.DebtNotificationMessage;
import com.valber.financial_control.application.usecases.user.ListUsersUseCase;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.domain.repository.DebtRepository;
import com.valber.financial_control.infrastructure.kafka.producers.DebtNotificationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DebtNotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DebtNotificationScheduler.class);

    private final DebtRepository debtRepository;
    private final ListUsersUseCase listUsersUseCase;
    private final DebtNotificationProducer debtNotificationProducer;

    public DebtNotificationScheduler(DebtRepository debtRepository,
                                     ListUsersUseCase listUsersUseCase,
                                     DebtNotificationProducer debtNotificationProducer) {
        this.debtRepository = debtRepository;
        this.listUsersUseCase = listUsersUseCase;
        this.debtNotificationProducer = debtNotificationProducer;
    }

    @Scheduled(cron = "0 0 5 * * ?") // Every day at 5 AM
    public void notifyUpcomingDebts() {
        logger.info("Starting scheduled task: notifyUpcomingDebts");

        LocalDate today = LocalDate.now();
        LocalDate twoDaysLater = today.plusDays(2);

        listUsersUseCase.listAllUsers().forEach(user -> {
            List<InstallmentDebt> upcomingInstallmentDebts = debtRepository.findUpcomingInstallmentDebts(
                    user.getId(), today, twoDaysLater);

            upcomingInstallmentDebts.stream()
                    .map(debt -> new DebtNotificationMessage(
                            user.getId(),
                            user.getUsername(),
                            debt.getId(),
                            debt.getDescription(),
                            debt.getInstallmentAmount(),
                            debt.getNextDueDate(),
                            debt.getCurrentInstallment(),
                            debt.getTotalInstallments()
                    ))
                    .forEach(debtNotificationProducer::sendDebtNotification);
        });

        logger.info("Finished scheduled task: notifyUpcomingDebts");
    }
}
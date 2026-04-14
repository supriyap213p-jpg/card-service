package com.bank.card.service;

import com.bank.card.dto.CardDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);

}

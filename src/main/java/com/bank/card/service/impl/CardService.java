package com.bank.card.service.impl;

import com.bank.card.constant.CardConstant;
import com.bank.card.dto.CardDto;
import com.bank.card.entity.Card;
import com.bank.card.exception.CardAlreadyExistsException;
import com.bank.card.exception.ResourceNotFoundException;
import com.bank.card.mapper.CardMapper;
import com.bank.card.repository.CardRepository;
import com.bank.card.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService implements ICardService {

    private final CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> card = cardRepository.findByMobileNumber(mobileNumber);//.orElseThrow(()->new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber));
        if (card.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(card(mobileNumber));
    }

    private Card card(String mobileNumber) {
        Card card = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardConstant.CREDIT_CARD);
        card.setTotalLimit(CardConstant.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstant.NEW_CARD_LIMIT);
        return card;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        CardMapper.mapToCard(cardDto, card);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardRepository.deleteById(card.getCardId());
        return true;
    }
}

package com.bank.card.controller;


import com.bank.card.constant.CardConstant;
import com.bank.card.dto.CardDto;
import com.bank.card.dto.ErrorResponseDto;
import com.bank.card.dto.ResponseDto;
import com.bank.card.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST APIs for Cards in EazyBank", description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details")
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final ICardService cardService;

    @Operation(summary = "Create Card REST API", description = "REST API to create new Card inside EazyBank")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "HTTP Status CREATED"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        cardService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder().statusCode(CardConstant.STATUS_201).statusMsg(CardConstant.MESSAGE_201).build());
    }


    @Operation(summary = "Fetch Card Details REST API", description = "REST API to fetch card details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        CardDto cardsDto = cardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }


    @Operation(summary = "Update Card Details REST API", description = "REST API to update card details based on a card number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "417", description = "Expectation Failed"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PutMapping
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = cardService.updateCard(cardDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardConstant.STATUS_200, CardConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardConstant.STATUS_417, CardConstant.MESSAGE_417_UPDATE));
        }
    }


    @Operation(summary = "Delete Card Details REST API", description = "REST API to delete Card details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "417", description = "Expectation Failed"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = cardService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardConstant.STATUS_200, CardConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardConstant.STATUS_417, CardConstant.MESSAGE_417_DELETE));
        }
    }


}

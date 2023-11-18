package com.example.demo.domain.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VoteDestinationDomainRequest {

    private Long chatRoomId;
    private Long memberId;
    private String voteResult;


}

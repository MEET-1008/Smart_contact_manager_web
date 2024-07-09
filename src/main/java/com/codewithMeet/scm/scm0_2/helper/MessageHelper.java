package com.codewithMeet.scm.scm0_2.helper;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageHelper {


    private String content;

    @Builder.Default
    private MessageType type = MessageType.blue ;
}

package com.example.demo.support.context;

import com.example.demo.security.context.TokenAuditHolder;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class MemberIdAuditHolder {

	public static Long get() {
		UserDetails userDetails = TokenAuditHolder.get();
		return Long.parseLong(userDetails.getUsername());
	}
}

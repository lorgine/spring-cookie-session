package com.oakfusion.security.serial;

import org.springframework.security.core.Authentication;

public interface AuthSerializer {

	Authentication read(byte[] bytes);
	byte[] write(Authentication authentication);
}

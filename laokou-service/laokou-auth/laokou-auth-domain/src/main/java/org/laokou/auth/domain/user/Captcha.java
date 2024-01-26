/*
 * Copyright (c) 2022-2024 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokou.auth.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.laokou.common.i18n.common.exception.AuthException;
import org.laokou.common.i18n.utils.ObjectUtil;
import org.laokou.common.i18n.utils.StringUtil;
import org.laokou.common.i18n.utils.ValidatorUtil;

import static lombok.AccessLevel.PRIVATE;
import static org.laokou.common.i18n.common.ErrorCodes.CAPTCHA_ERROR;
import static org.laokou.common.i18n.common.ErrorCodes.CAPTCHA_EXPIRED;
import static org.laokou.common.i18n.common.StatusCodes.CUSTOM_SERVER_ERROR;
import static org.laokou.common.i18n.common.ValCodes.OAUTH2_CAPTCHA_REQUIRE;
import static org.laokou.common.i18n.common.ValCodes.OAUTH2_UUID_REQUIRE;

/**
 * @author laokou
 */
@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
@Schema(name = "Captcha", description = "验证码")
public class Captcha {

	@Schema(name = "uuid", description = "唯一标识")
	String uuid;

	@Schema(name = "captcha", description = "验证码")
	String captcha;

	public void checkNullCaptcha() {
		if (StringUtil.isEmpty(this.captcha)) {
			throw new AuthException(CUSTOM_SERVER_ERROR, ValidatorUtil.getMessage(OAUTH2_CAPTCHA_REQUIRE));
		}
	}

	public void checkNullUUID() {
		if (StringUtil.isEmpty(this.uuid)) {
			throw new AuthException(CUSTOM_SERVER_ERROR, ValidatorUtil.getMessage(OAUTH2_UUID_REQUIRE));
		}
	}

	public void checkCaptcha(Boolean validateResult) {
		if (ObjectUtil.isNull(validateResult)) {
			throw new AuthException(CAPTCHA_EXPIRED);
		}
		if (!validateResult) {
			throw new AuthException(CAPTCHA_ERROR);
		}
	}

}
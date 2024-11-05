/*
 * Copyright (c) 2022-2024 KCloud-Platform-IoT Author or Authors. All Rights Reserved.
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

package org.laokou.admin.noticeLog.command.query;

import lombok.RequiredArgsConstructor;
import org.laokou.admin.noticeLog.convertor.NoticeLogConvertor;
import org.laokou.admin.noticeLog.dto.NoticeLogPageQry;
import org.laokou.admin.noticeLog.dto.clientobject.NoticeLogCO;
import org.laokou.admin.noticeLog.gatewayimpl.database.NoticeLogMapper;
import org.laokou.admin.noticeLog.gatewayimpl.database.dataobject.NoticeLogDO;
import org.laokou.common.core.utils.ThreadUtil;
import org.laokou.common.i18n.common.exception.SystemException;
import org.laokou.common.i18n.dto.Page;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 分页查询通知日志请求执行器.
 *
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class NoticeLogPageQryExe {

	private final NoticeLogMapper noticeLogMapper;

	public Result<Page<NoticeLogCO>> execute(NoticeLogPageQry qry) {
		try (ExecutorService executor = ThreadUtil.newVirtualTaskExecutor()) {
			CompletableFuture<List<NoticeLogDO>> c1 = CompletableFuture
				.supplyAsync(() -> noticeLogMapper.selectObjectPage(qry.index()), executor);
			CompletableFuture<Long> c2 = CompletableFuture.supplyAsync(() -> noticeLogMapper.selectObjectCount(qry),
					executor);
			return Result
				.ok(Page.create(c1.get(30, TimeUnit.SECONDS).stream().map(NoticeLogConvertor::toClientObject).toList(),
						c2.get(30, TimeUnit.SECONDS)));
		}
		catch (Exception e) {
			throw new SystemException("S_NoticeLog_PageQueryTimeout", "通知日志分页查询超时");
		}
	}

}
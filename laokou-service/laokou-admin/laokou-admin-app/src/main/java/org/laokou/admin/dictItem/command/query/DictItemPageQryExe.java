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

package org.laokou.admin.dictItem.command.query;

import lombok.RequiredArgsConstructor;
import org.laokou.admin.dictItem.dto.DictItemPageQry;
import org.laokou.admin.dictItem.dto.clientobject.DictItemCO;
import org.laokou.admin.dictItem.gatewayimpl.database.DictItemMapper;
import org.laokou.admin.dictItem.gatewayimpl.database.dataobject.DictItemDO;
import org.laokou.common.i18n.dto.Page;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;
import org.laokou.admin.dictItem.convertor.DictItemConvertor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 分页查询字典项请求执行器.
 *
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class DictItemPageQryExe {

	private final DictItemMapper dictItemMapper;

	private final Executor executor;

	@SneakyThrows
	public Result<Page<DictItemCO>> execute(DictItemPageQry qry) {
		CompletableFuture<List<DictItemDO>> c1 = CompletableFuture
			.supplyAsync(() -> dictItemMapper.selectPageByCondition(qry), executor);
		CompletableFuture<Long> c2 = CompletableFuture.supplyAsync(() -> dictItemMapper.selectCountByCondition(qry),
				executor);
		return Result
			.ok(Page.create(c1.get(30, TimeUnit.SECONDS).stream().map(DictItemConvertor::toClientObject).toList(),
					c2.get(30, TimeUnit.SECONDS)));
	}

}
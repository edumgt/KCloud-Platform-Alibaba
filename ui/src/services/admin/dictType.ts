// @ts-ignore
/* eslint-disable */
import {request} from '@umijs/max';

/** 修改字典类型 修改字典类型 PUT /v3/dict-types */
export async function modifyV3(body: API.DictTypeModifyCmd, options?: { [key: string]: any }) {
	return request<any>('/v3/dict-types', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json',
		},
		data: body,
		...(options || {}),
	});
}

/** 保存字典类型 保存字典类型 POST /v3/dict-types */
export async function saveV3(body: API.DictTypeSaveCmd, options?: { [key: string]: any }) {
	return request<any>('/v3/dict-types', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		data: body,
		...(options || {}),
	});
}

/** 删除字典类型 删除字典类型 DELETE /v3/dict-types */
export async function removeV3(body: number[], options?: { [key: string]: any }) {
	return request<any>('/v3/dict-types', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		},
		data: body,
		...(options || {}),
	});
}

/** 查看字典类型详情 查看字典类型详情 GET /v3/dict-types/${param0} */
export async function getByIdV3(
	// 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
	params: API.getByIdV311Params,
	options?: { [key: string]: any },
) {
	const {id: param0, ...queryParams} = params;
	return request<API.Result>(`/v3/dict-types/${param0}`, {
		method: 'GET',
		params: {...queryParams},
		...(options || {}),
	});
}

/** 导出字典类型 导出字典类型 POST /v3/dict-types/export */
export async function exportV3(body: API.DictTypeExportCmd, options?: { [key: string]: any }) {
	return request<any>('/v3/dict-types/export', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		data: body,
		...(options || {}),
	});
}

/** 导入字典类型 导入字典类型 POST /v3/dict-types/import */
export async function importV3(body: {}, file?: File[], options?: { [key: string]: any }) {
	const formData = new FormData();

	if (file) {
		file.forEach((f) => formData.append('file', f || ''));
	}

	Object.keys(body).forEach((ele) => {
		const item = (body as any)[ele];

		if (item !== undefined && item !== null) {
			if (typeof item === 'object' && !(item instanceof File)) {
				if (item instanceof Array) {
					item.forEach((f) => formData.append(ele, f || ''));
				} else {
					formData.append(ele, JSON.stringify(item));
				}
			} else {
				formData.append(ele, item);
			}
		}
	});

	return request<any>('/v3/dict-types/import', {
		method: 'POST',
		data: formData,
		requestType: 'form',
		...(options || {}),
	});
}

/** 分页查询字典类型列表 分页查询字典类型列表 POST /v3/dict-types/page */
export async function pageV3(body: API.DictTypePageQry, options?: { [key: string]: any }) {
	return request<API.Result>('/v3/dict-types/page', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		data: body,
		...(options || {}),
	});
}
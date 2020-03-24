package com.spring.nebula.api.service;

import java.util.List;

import com.spring.nebula.api.entity.ErrorHandleLogVo;

public interface HandErrorLogService {

	List<ErrorHandleLogVo> getLogInfos();
}

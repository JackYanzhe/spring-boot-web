package com.spring.nebula.api.dao;

import java.util.List;

import com.spring.nebula.api.entity.ErrorHandleLogVo;
import com.spring.nebula.common.annotation.MysqlRepository;
@MysqlRepository
public interface HandErrorLogDao {

	List<ErrorHandleLogVo> getLogInfos();
}

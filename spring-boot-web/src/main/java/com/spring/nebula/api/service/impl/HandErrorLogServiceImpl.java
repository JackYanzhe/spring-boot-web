package com.spring.nebula.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.nebula.api.dao.HandErrorLogDao;
import com.spring.nebula.api.entity.ErrorHandleLogVo;
import com.spring.nebula.api.service.HandErrorLogService;

@Service
public class HandErrorLogServiceImpl implements HandErrorLogService{

	@Autowired
	public HandErrorLogDao handErrorLogDao;
	
	  private static final Logger LOG = LoggerFactory.getLogger(HandErrorLogServiceImpl.class);
	@Override
	public List<ErrorHandleLogVo> getLogInfos() {
		List<ErrorHandleLogVo> logInfos = new ArrayList<>();
		try {
			logInfos =handErrorLogDao.getLogInfos();
		} catch (Exception e) {
			LOG.info("出现异常信息："+e.getMessage());
			throw new RuntimeException();
		}
		
		return logInfos;
	}

}

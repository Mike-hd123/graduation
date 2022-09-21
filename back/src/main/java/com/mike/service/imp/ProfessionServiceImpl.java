package com.mike.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.mike.dao.ProfessionDao;
import com.mike.dao.entity.Profession;
import com.mike.service.inter.ProfessionService;

import org.springframework.stereotype.Service;

/**
 * Description
 * Author: Mike-hd123
 * Date2020/3/31 17:33
 **/
@Service
public class ProfessionServiceImpl implements ProfessionService {
  @Resource
  private ProfessionDao professionDao;

  @Override
  public List<Profession> getProfessionList() {
    return professionDao.getProfessionList();
  }
}

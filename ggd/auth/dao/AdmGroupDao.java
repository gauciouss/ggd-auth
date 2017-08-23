package ggd.auth.dao;

import org.springframework.stereotype.Repository;

import ggd.auth.vo.AdmGroup;
import ggd.core.db.HibernateDao;

@Repository("AdmGroupDao")
public class AdmGroupDao extends HibernateDao<AdmGroup, String> {

}

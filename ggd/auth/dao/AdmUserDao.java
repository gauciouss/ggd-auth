package ggd.auth.dao;

import org.springframework.stereotype.Repository;

import ggd.auth.vo.AdmUser;
import ggd.core.db.HibernateDao;

@Repository("AdmUserDao")
public class AdmUserDao extends HibernateDao<AdmUser, String> {

	
}

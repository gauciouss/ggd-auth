package ggd.auth.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

public class AdmUser implements Serializable {

	@Id
	@Column(name = "account")
	private String account;
	
	private String pwd;
	
	private String email;
	
	private String address;
	
	private String tel;
	
	private String phone;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	private boolean isEnabled;
	
	private boolean isApproved;
	
	private AdmGroup group;
	
	private List<AdmFunc> funcs;
}

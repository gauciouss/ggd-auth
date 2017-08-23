package ggd.auth.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Adm_Group")
public class AdmGroup {

	@Id
	@Column(name = "group_id")
	private String groupId;
	
	@Column(name = "group_name")
	private String groupName;
	
	@Column(name = "create_date")
	private Timestamp createDate;
	
	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@Column(name = "isEnabled")
	private boolean isEnabled;
	
	@Column(name = "isApproved")
	private boolean isApproved;
	
	private List<AdmFunc> funcs;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
	private List<AdmUser> users;
}

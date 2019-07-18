package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IDistrictService;

/**
 * 处理省、市、区数据的业务层实现类
 */
@Service
public class DistrictServiceImpl implements IDistrictService {

	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getByParent(String parent) {
		return findByParent(parent);
	}
	
	@Override
	public District getByCode(String code) {
		return findByCode(code);
	}
	
	/**
	 * 获取全国所有省/某省所有市/某市所有区的列表
	 * @param parent 父级单位的代号，如果获取全国所有省，则使用86作为父级代号
	 * @return 匹配的省或市或区的列表
	 */
	private List<District> findByParent(String parent) {
		return districtMapper.findByParent(parent);
	}
	
	/**
	 * 根据省/市/区的代号，获取数据详情
	 * @param code 省/市/区的代号
	 * @return 匹配的数据详情，如果没有匹配的数据，则返回null
	 */
	private District findByCode(String code) {
		return districtMapper.findByCode(code);
	}


}

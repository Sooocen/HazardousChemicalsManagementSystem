package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.domain.Tree;
import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.common.utils.SortUtil;
import com.socen.ws.common.utils.TreeUtil;
import com.socen.ws.gen.entity.Company;
import com.socen.ws.gen.entity.Zone;
import com.socen.ws.gen.dao.ZoneMapper;
import com.socen.ws.gen.entity.model.Option;
import com.socen.ws.gen.service.ICompanyService;
import com.socen.ws.gen.service.IZoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author Socen
 */
@Slf4j
@Service("IZoneService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZoneServiceImpl extends ServiceImpl<ZoneMapper, Zone> implements IZoneService {

    @Autowired
    private ICompanyService companyService;

    @Override
    public Map<String, Object> findZones(QueryRequest request, Zone zone) {
        Map<String,Object> result = new HashedMap<>();
        try{
            List<Zone> zones = findZones(zone,request);
            List<Tree<Zone>> trees = new ArrayList<>();
            buildTrees(trees,zones);
            Tree<Zone> zoneTree = TreeUtil.build(trees);
            result.put("rows",zoneTree);
            result.put("total",zones.size());
        }catch (Exception e){
            log.error("获取区域列表失败",e);
            result.put("rows",null);
            result.put("total",0);
        }
        return result;
    }

    @Override
    public List<Zone> findZones(Zone zone, QueryRequest request) {
        QueryWrapper<Zone> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(zone.getZoneName())){
            queryWrapper.lambda().eq(Zone::getZoneName,zone.getZoneName());
        }
        if (StringUtils.isNotBlank(zone.getCreateTimeFrom()) && StringUtils.isNotBlank(zone.getCreateTimeTo())){
            queryWrapper.lambda()
                    .eq(Zone::getCreateTime,zone.getCreateTimeFrom())
                    .le(Zone::getCreateTime,zone.getCreateTimeTo());
        }
        SortUtil.handleWrapperSort(request,queryWrapper,"zoneNum", WsConstant.ORDER_ASC,true);
        return this.baseMapper.selectList(queryWrapper);
    }

    private void buildTrees(List<Tree<Zone>> trees,List<Zone> zones){
        zones.forEach(zone -> {
            Tree<Zone> tree = new Tree<>();
            tree.setId(zone.getZoneId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(zone.getParentId().toString());
            tree.setText(zone.getZoneName());
            tree.setCreateTime(zone.getCreateTime());
            tree.setModifyTime(zone.getModifyTime());
            tree.setOrder(zone.getZoneNum());
            tree.setTitle(tree.getText());
            tree.setValue(zone.getPoints());
            trees.add(tree);
        });
    }

    @Override
    public void createZone(Zone zone) {
        Long parentId = zone.getParentId();
        if (parentId == null){
            zone.setParentId(0L);
        }
        zone.setCreateTime(new Date());
        zone.setModifyTime(new Date());
        this.save(zone);
    }

    @Override
    public void updateZone(Zone zone) {
        zone.setModifyTime(new Date());
        this.baseMapper.updateById(zone);
    }

    @Override
    public void deleteZones(String[] zoneIds) throws WsException{
        try {
            this.delete(Arrays.asList(zoneIds));
        }catch (Exception e){
            log.error("删除失败",e);
            throw new WsException("删除失败");
        }

    }

    @Override
    public List<Zone> findZoneByUsername(String username) {
        return this.baseMapper.findZoneByUsername(username);
    }

    @Override
    public List<Option> findZoneCompanyOptionByUsername(String username) {
        List<Option> optionList = new ArrayList<>();
        List<Zone> zones = this.baseMapper.findZoneByUsername(username);
        zones.forEach(zone -> {
            Option option = new Option();
            option.setValue(zone.getZoneId().toString());
            option.setLabel(zone.getZoneName());
            option.setData(zone.getPoints());
            List<Option> childrenList = new ArrayList<>();
            List<Company> companyList = companyService.findCompanyByZoneId(zone.getZoneId().toString());
            companyList.forEach(company -> {
                Option op = new Option();
                op.setLabel(company.getCompanyName());
                op.setValue(company.getCompanyId().toString());
                op.setData(company.getCompanyLat()+","+company.getCompanyLon());
                childrenList.add(op);
            });
            option.setChildren(childrenList);
            optionList.add(option);
        });
        return optionList;
    }

    private void delete(List<String> zoneIds) throws WsException {
        try {
            if(zoneIds == null){
                return;
            }
            removeByIds(zoneIds);
            LambdaQueryWrapper<Zone> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Zone::getParentId, zoneIds);
            List<Zone> zones = baseMapper.selectList(queryWrapper);
            if (zones.size() > 0) {
                List<String> zoneIdList = new ArrayList<>();
                zones.forEach(d -> zoneIdList.add(String.valueOf(d.getZoneId())));
                this.delete(zoneIdList);
            }
        } catch (Exception e){
            log.error("删除出现错误",e);
            throw new WsException("删除出现错误");
        }
    }


}

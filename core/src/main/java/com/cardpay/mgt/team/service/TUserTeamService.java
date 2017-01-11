package com.cardpay.mgt.team.service;

import com.cardpay.basic.base.service.BaseService;
import com.cardpay.mgt.team.model.TUserTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户团队关联表
 *
 * @author chenkai
 *         createTime 2017-01-2017/1/6 10:39
 */
public interface TUserTeamService extends BaseService<TUserTeam> {
    /**
     * 批量添加团队,成员关联信息
     * @param teamId 团队Id
     * @param userIds 成员id
     * @return 数据库变记录
     */
    int batchInsert (int teamId, String userIds);

    /**
     * 按进件Id查询所属团队信息
     * @param applicationId 进件id
     * @return 用户团队信息
     */
    List<TUserTeam> queryTeamByAppId(int applicationId);

}

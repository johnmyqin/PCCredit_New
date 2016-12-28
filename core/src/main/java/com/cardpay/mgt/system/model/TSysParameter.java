package com.cardpay.mgt.system.model;

import com.cardpay.basic.base.model.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Lazy;

import java.util.Date;
import javax.persistence.*;

/**
 * 系统参数配置
 * @author chenkai
 */
@Lazy
@Table(name = "T_SYS_PARAMETER")
@ApiModel(value="系统参数配置")
public class TSysParameter extends GenericEntity<Integer>{
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SYS_PARAMETER_SEQ.nextval from dual")
    @ApiModelProperty(value="主键",required = true)
    private Integer id;

    /**
     * 参数名称
     */
    @Column(name = "PARAMETER_NAME")
    @ApiModelProperty(value="参数名称",required = true)
    private String parameterName;

    /**
     * 参数值
     */
    @Column(name = "PARAMETER_VALUE")
    @ApiModelProperty(value="参数值",required = true)
    private String parameterValue;

    /**
     * 参数中文名
     */
    @Column(name = "PARAMETER_NAME_ZN")
    @ApiModelProperty(value="参数中文名",required = true)
    private String parameterNameZn;

    /**
     * 参数描述
     */
    @Column(name = "PARAMETER_DESCRIPTION")
    @ApiModelProperty(value="参数描述",required = true)
    private String parameterDescription;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    @ApiModelProperty(value="创建人",required = true)
    private Integer createBy;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value="创建时间",required = true)
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "MODIFY_BY")
    @ApiModelProperty(value="修改人",required = true)
    private Integer modifyBy;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value="修改时间",required = true)
    private Date modifyTime;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取参数名称
     *
     * @return PARAMETER_NAME - 参数名称
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * 设置参数名称
     *
     * @param parameterName 参数名称
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * 获取参数值
     *
     * @return PARAMETER_VALUE - 参数值
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     * 设置参数值
     *
     * @param parameterValue 参数值
     */
    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    /**
     * 获取参数中文名
     *
     * @return PARAMETER_NAME_ZN - 参数中文名
     */
    public String getParameterNameZn() {
        return parameterNameZn;
    }

    /**
     * 设置参数中文名
     *
     * @param parameterNameZn 参数中文名
     */
    public void setParameterNameZn(String parameterNameZn) {
        this.parameterNameZn = parameterNameZn;
    }

    /**
     * 获取参数描述
     *
     * @return PARAMETER_DESCRIPTION - 参数描述
     */
    public String getParameterDescription() {
        return parameterDescription;
    }

    /**
     * 设置参数描述
     *
     * @param parameterDescription 参数描述
     */
    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    /**
     * 获取创建人
     *
     * @return CREATE_BY - 创建人
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return MODIFY_BY - 修改人
     */
    public Integer getModifyBy() {
        return modifyBy;
    }

    /**
     * 设置修改人
     *
     * @param modifyBy 修改人
     */
    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * 获取修改时间
     *
     * @return MODIFY_TIME - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public Integer getPK() {
        return id;
    }
}
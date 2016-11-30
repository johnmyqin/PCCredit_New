package com.cardpay.mgt.organization.service.impl;

import com.cardpay.mgt.organization.dao.TOrganizationMapper;
import com.cardpay.mgt.organization.model.vo.TOrganizationVo;
import com.cardpay.mgt.organization.service.TOrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by chenkai on 2016/11/30.
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({TOrganizationServiceImpl.class})
public class TOrganizationServiceImplTest {
    @Mock
    private TOrganizationMapper tOrganizationDao;

    @InjectMocks
    private TOrganizationServiceImpl tOrganizationService;


    @Test
    public void queryOrganization() throws Exception {
        List<TOrganizationVo> list = new ArrayList<>();
        TOrganizationVo tOrganizationVo = new TOrganizationVo();
        list.add(tOrganizationVo);
        when(tOrganizationDao.queryOrganization(1)).thenReturn(list);
        List<TOrganizationVo> tOrganizationVos = tOrganizationService.queryOrganization(1, 3);
        assertTrue(tOrganizationVos.size() > 0);
        verify(tOrganizationDao).createOrganizationView(1, 3);

    }

    @Test
    public void deleteOrganization() throws Exception {
        when(tOrganizationDao.deleteOrganization(1)).thenReturn(1);
        int flag = tOrganizationService.deleteOrganization(1);
        assertEquals(1, flag);
    }

}
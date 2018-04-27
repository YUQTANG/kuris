package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.mapper.ClientMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    public void saveOrUpdate(Client client) {
        if (client.getId() == null) {
            clientMapper.insert(client);
        } else {
            clientMapper.updateByPrimaryKey(client);
        }
    }

    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    public List<Client> list() {
        return clientMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = clientMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = clientMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}

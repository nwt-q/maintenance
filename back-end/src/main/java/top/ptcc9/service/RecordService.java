package top.ptcc9.service;

import top.ptcc9.common.R;
import top.ptcc9.controller.vo.ReCordUpdataReqVO;
import top.ptcc9.controller.vo.RecordDataReqVO;
import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author NWT
* @description 针对表【record】的数据库操作Service
* @createDate 2025-06-11 00:44:19
*/
public interface RecordService extends IService<Record> {

    List<WorkOrderReqVO> getlist(WorkOrderReqVO rt);

    List<Record> getlists(String equipmentName, String categoryName, String userId, String equipmentId);

    List<Record> getviewRecord(String equipmentId);

    void updateRecord(Record record);

    void updateRecord2(Record record);

    R<Boolean> createRecord(Record record);

    void UpdataRecord(Record rs, ReCordUpdataReqVO formdata);

    RecordDataReqVO getRecord(String mobile);

    List<WorkOrderReqVO> getRecord2(String mobile);

    String getMonthRate(String mobile);
}


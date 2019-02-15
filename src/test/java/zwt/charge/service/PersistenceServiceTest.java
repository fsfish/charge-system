package zwt.charge.service;

import org.junit.Test;

import javax.annotation.Resource;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午7:43 2019/1/23
 */
public class PersistenceServiceTest extends SpringTest {

    @Resource
    private PersistenceService persistenceService;

    @Test
    public void checkActived() throws ParseException {
        persistenceService.checkActived();
    }

    @Test
    public void keepMemoryData() {

    }
}

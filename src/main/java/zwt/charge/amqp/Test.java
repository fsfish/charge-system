package zwt.charge.amqp;

import zwt.charge.bean.VideoDataBean;
import zwt.charge.core.Constant;
import zwt.charge.core.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午5:15 2019/1/24
 */
public class Test {

    public static void main(String[] args) {
        List<VideoDataBean> videoDataBeanList = new ArrayList<>();

        VideoDataBean videoDataBean1 = new VideoDataBean();
        videoDataBean1.setId("duobei_t1");
        videoDataBean1.setStreamId("duobei_s2");
        videoDataBean1.setStreamType(Constant.AUDIO_TYPE.trim());
        videoDataBean1.setFlow(1);
        videoDataBean1.setStartTime("");
        videoDataBean1.setEndTime("");
        videoDataBean1.setCreateTime(new Date());
        videoDataBean1.setOriginTimestamp(1548227781612L);
        videoDataBeanList.add(videoDataBean1);


        VideoDataBean videoDataBean2 = new VideoDataBean();
        videoDataBean1.setId("duobei_t1");
        videoDataBean1.setStreamId("duobei_s2");
        videoDataBean1.setStreamType(Constant.AUDIO_TYPE.trim());
        videoDataBean1.setFlow(1);
        videoDataBean1.setStartTime("");
        videoDataBean1.setEndTime("");
        videoDataBean1.setCreateTime(new Date());
        videoDataBean1.setOriginTimestamp(1548227781612L);
        videoDataBeanList.add(videoDataBean1);


        VideoDataBean videoDataBean3 = new VideoDataBean();
        videoDataBean1.setId("duobei_t1");
        videoDataBean1.setStreamId("duobei_s2");
        videoDataBean1.setStreamType(Constant.AUDIO_TYPE.trim());
        videoDataBean1.setFlow(1);
        videoDataBean1.setStartTime("");
        videoDataBean1.setEndTime("");
        videoDataBean1.setCreateTime(new Date());
        videoDataBean1.setOriginTimestamp(1548227781612L);
        videoDataBeanList.add(videoDataBean1);

        VideoDataBean videoDataBean4 = new VideoDataBean();
        videoDataBean1.setId("duobei_t1");
        videoDataBean1.setStreamId("duobei_s2");
        videoDataBean1.setStreamType(Constant.AUDIO_TYPE.trim());
        videoDataBean1.setFlow(1);
        videoDataBean1.setStartTime("");
        videoDataBean1.setEndTime("");
        videoDataBean1.setCreateTime(new Date());
        videoDataBean1.setOriginTimestamp(1548227781612L);
        videoDataBeanList.add(videoDataBean1);

        Collections.sort(videoDataBeanList, new Comparator<VideoDataBean>() {
            @Override
            public int compare(VideoDataBean o1, VideoDataBean o2) {
                Date o1StartTime = null;
                Date o2StartTime = null;
                try {
                    o1StartTime = DateUtils.dateOriToDate(o1.getStartTime());
                    o2StartTime = DateUtils.dateOriToDate(o2.getStartTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (o1StartTime.before(o2StartTime)) {
                    return -1;
                } else if (o1StartTime.after(o2StartTime)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


    }

}

package com.billow.job.constant;

/**
 * @author liuyongtao
 * @create 2019-09-26 15:25
 */
public class JobCst {

    // 是否发送邮件，0-不发送
    public static final String JOB_FC_SEND_MAIL_NO_SEND = "0";

    // 放入 JobData 中的 key
    public static final String SCHEDULE_JOB_VO = "ScheduleJobVo";

    // ScheduleJobLogService 实现类 bean id
    public static final String SCHEDULE_JOB_SERVICE_IMPL = "scheduleJobServiceImpl";

    // ScheduleJobLogService 实现类 bean id
    public static final String SCHEDULE_JOB_LOG_SERVICE_IMPL = "scheduleJobLogServiceImpl";

    // JobService 实现类 bean id
    public static final String JOB_SERVICE_IMPL = "jobServiceImpl";

    // 执行类型：1-spring id
    public static final String CLASS_TYPE_SPRING_ID = "1";
    // 执行类型：2-全类名
    public static final String CLASS_TYPE_BEAN_CLASS = "2";
}
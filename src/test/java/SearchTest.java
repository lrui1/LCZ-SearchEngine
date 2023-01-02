import ES.impl.ESearch;
import Bean.ResultEntry;
import utils.ESUtil;

import java.io.IOException;
import java.util.List;

public class SearchTest {
    public static void main(String[] args) throws IOException {
        ESearch es = new ESearch();
        List<ResultEntry> resultEntryList = es.search("计算机工程学院");
        for(ResultEntry resultEntry : resultEntryList) {
            System.out.println(resultEntry);
        }
        ESUtil.release();
        // search data by ES
//        SearchResponse<ResultEntry> response = client.search(s -> s
//                        .index(ESUtil.index)
//                        .query(q -> q
//                                .match(t -> t
//                                        .field("text")
//                                        .query("计算机工程学院")
//                                )
//                        ),
//                ResultEntry.class
//        );

//         add data -> ES
//        ResultEntry resultEntry = new ResultEntry("http://cec.jmu.edu.cn/", "计算机工程学院", "关闭 English 学院首页 学院概况 学院简介 组织机构 专业设置 学院风光 党建思政 通知通告 党的建设 思政学习 时事动态 师资队伍 教授 副教授 讲师-博士 本科生教育 通知通告 毕业设计 政策文件 教学改革 专业培养方案 下载中心 研究生教育 通知公告 管理工作 专业介绍 导师介绍 科学研究 通知公告 管理工作 项目成果 研究机构 学生工作 通知通告 创新创业 实习就业 校园生活 招生就业 招生信息 就业信息 校友之家 校友活动 出彩计算机人 校友捐赠 办事大厅 学校办事大厅 学院办事清单 下载表格 < > 学院新闻 09 2022/12 计算机工程学院党校举办第三期入党积极分子培训班 为加强入党积极分子的培养教育，确保发展党员的质量，2022年11月16日至23日，计算... 04 2022/11 计算机工程学院携手智业企业共建教学实习基地 2022年11月2日，计算机工程学院联合智业软件在智业总部进行实习基地签约及授牌仪式... 02 2022/11 集美大学、龙芯中科与高鸿信安签订合作协议，共建可信计算联合实验室 集美大学、龙芯中科与高鸿信安签订合作协议，共建可信计算联合实验室 近日，集美大... 01 2022/11 计算机工程学院举办2022年荣休教师座谈会 近日，计算机工程学院于陆大楼506会议室举办荣休教师座谈会，学院领导、教研室主任... 每周会议 2022 12-12计算机工程学院2022-2023学年上学期第16周会议安排表 2022 12-05计算机工程学院2022-2023学年上学期第15周会议安排表 2022 11-28计算机工程学院2022-2023学年上学期第14周会议安排表 院务公开 2022 12-09关于计算机工程学院实验技术人员2017-2021聘期考核及2021-202... 2022 11-30计算机工程学院2021-2022学年教职工奖励性绩效工资发放情况公... 2022 11-08计算机工程学院关于对陈景铎学生退学处理的公示 通知公告 2022-11-14学术报告：深度学习在微表情识别的研究 2022-05-11第六届电子信息技术与计算机工程国际学术会议（EITCE 2022... 2022-04-09计算机工程学院2022年研究生招生调剂（第一轮）总成绩公示 教学管理 2022-12-01关于2022-2023学年第二学期选用的教材公示 2022-11-24计算机工程学院 2022年集美大学本科教学优秀奖评选结果公告 2022-11-08计算机工程学院关于对陈景铎学生退学处理的公示 学院风采 计算机工程学院策划“三心”活动喜迎新IT人 第五届集美大学程序设计竞赛顺利举办 计算机工程学院第十四届辩论赛决赛成功举行 集美大学第九届全国大学生电子商务“创新、创意及创业”挑战... 集美大学计算机工程学院“网盒杯”篮球决赛顺利举行 计算机工程学院第二届“一站到底”知识竞赛决赛顺利举行 学术科研 学术报告：深度学习在微表情识别的研究 关于启动2023年度国家自然科学基金项目申报工作的通知 关于福建省知识产权局做好第二十四届中国专利奖组织推荐工作... 集美大学智慧城市创新实验室暑期学术研讨会公告 第六届电子信息技术与计算机工程国际学术会议（EITCE 2022）... 学生事务 关于组织参加全国大学生信息安全竞赛校内选拔赛的通知 关于组织参加大学生计算机设计大赛的通知 关于2022年中国高校计算机大赛-网络技术挑战赛校赛通知 计算机工程学院与中电金信软件有限公司签订共建合作协议 学长学姐面对面交流活动成功举行 友情链接 集美大学 院长信箱 书记信箱 地址：厦门市集美区银江路183号陆大楼 邮编：361021");
//        IndexResponse response = client.index(i -> i.index(ESUtil.index).document(resultEntry).id("3"));
//        System.out.println("response.result() = " + response.result());
//        System.out.println("response.id() = " + response.id());
//        System.out.println("response.seqNo() = " + response.seqNo());
//        System.out.println("response.index() = " + response.index());
//        System.out.println("response.shards() = " + response.shards());
    }
}

Tweet 主函数，调用连接数据库的方法
ConnectorDb 连接数据库的方法
测试时避开Tweet_Db数据库，即最大的tweet数据库，因为仍然在添加数据。
black_spots_2007-2012_vic 建议使用该car crash数据库测试

通过创建view，使用mapreduce获取数据
因为tweet_Db还在增长，所以没有在该数据库中创建view

tweet_db database related API: TweetRepository 获取位置在墨尔本并且发布的信息中含有骑车相关关键词的tweet的方法
black_spots_2007-2012_vic related API： TrafficRepository 获取位置在墨尔本的所有交通事故的方法
accidents 指明该位置发生事故的数量，如果利用该变量，则可以在显示中用圆圈大小或颜色等区分每个位置的交通事故发生频率。如果不用，也ok

CouchDb address: http://130.56.253.207:5984/_utils

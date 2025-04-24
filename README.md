
# wth-dts 项目说明

本项目为基于 Spring Boot 和 Spring Cloud 的分布式数据订阅与处理平台，采用 Maven 多模块结构，集成 Kafka 消费、阿里云 DTS、分布式缓存、数据库连接池等核心技术。

## 项目结构

```text
wth-dts/
├── simple-kafka-comsumer       Kafka 消费模块
├── wth-dts-base                公共基础模块（工具类、通用配置等）
├── system-dts-template         数据订阅核心逻辑模块
├── pom.xml                     根模块配置文件
```

## 核心技术栈

- **Java 版本**：1.8
- **Spring Boot**：2.7.18
- **Spring Cloud**：2021.0.9
- **Spring Cloud Alibaba**：2021.0.6.1
- **Kafka 客户端**：2.7.0
- **阿里云 DTS 订阅 SDK**：2.1.4
- **Fastjson**：2.0.39
- **Lombok**：1.18.20
- **Disruptor 高性能队列**：3.4.2
- **Caffeine 缓存**：3.1.8
- **数据库**：MySQL（驱动版本 8.0.28）
- **数据库连接池**：Druid（1.2.11）

## 快速开始

### 克隆项目

```bash
git clone https://github.com/Jonlson/wth-dts.git
cd wth-dts
```

### 构建项目

确保已安装 Maven 和 JDK 1.8+，执行以下命令进行构建：

```bash
mvn clean install -DskipTests
```

### 启动模块

根据实际需要可分别启动 `simple-kafka-comsumer` 或 `system-dts-template` 模块。你可以使用如下命令：

```bash
cd simple-kafka-comsumer
mvn spring-boot:run
```

## 配置说明

各模块中的 `application.yml` 或 `application.properties` 中配置了 Kafka、DTS、数据库等相关参数，使用前请根据你的实际环境进行调整。

## 开发者信息

- **姓名**：wth  
- **邮箱**：2760368904@qq.com  
- **组织**：软件开发部  
- **GitHub**：[https://github.com/Jonlson/wth-dts](https://github.com/Jonlson/wth-dts)

## License

本项目仅供学习交流，禁止用于商业用途。


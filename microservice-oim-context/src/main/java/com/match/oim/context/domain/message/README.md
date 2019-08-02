# 消息的定义

### 文本消息 
``` json
TEXT
{
    "text":"flyants-chat 你好!"
}
```
### 图片消息 
``` json
IMAGE
{
    "small":"http://small.jpg", // 缩略图
    "source":"http://source.jpg" //原图
}
```
### 语音消息
``` json
AUDIO
{
    "url":"http://small.mp3", //语音地址
    "duration":16 //语音时长
}
``` 
### 好友申请消息
``` json
FRIENDS_APPLY
{
    "applyMessageUserId":"111",//申请人ID
    "applyNickName":"达尔文", //申请人昵称
    "applyEncodedPrincipal":"http://sss.jpg", //申请人头像
    "status":"0,1,2" //申请/同意/拒绝
    "reject":"你介错人了"
}
``` 

### 消息处理器
``` java
public interface MessageHandler {

    @JsonIgnore
    MessageType getMessageType();

    @JsonIgnore
    String toBody();

    @JsonIgnore
    MessageHandler builder(String body);
}

```
# WorldAliasVariables

为你的世界设置任何别名且以占位符的形式应用在其他地方。

## 功能

### 占位符
- `%wav%` - 显示玩家当前世界的别名
- `%wav_<world>%` - 显示指定世界的别名
  - 例如：`%wav_world%` 显示主世界别名
  - 例如：`%wav_world_nether%` 显示下界别名
  - 例如：`%wav_world_the_end%` 显示末地别名

### 命令
- `/wav reload` - 重载配置文件
- `/wav set <世界名> <别名>` - 设置世界别名
- `/wav list` - 查看所有世界别名

### 权限
- `wav.admin` - 允许使用所有命令（默认OP）

### 配置文件
支持自定义：
- 世界别名
- 调试模式
- 颜色代码 
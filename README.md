# ShadowMotd
PlaceholderAPIの値をMOTD 3行目以降に追加するプラグイン<br>
motd を経由してサーバー内の情報を外部アプリケーションで使用することを想定しています。


## 前提
- Spigot 1.13 以上 (またはその派生)
- PlaceholderAPI

## コマンドと権限
- なし

## 設定
```yml
# motd 3行目以降(最後の行)に挿入される値
values:
  - input: "%server_has_whitelist%"  # 入力される文字列 (PAPI可)
    pattern: "^yes$"  # 正規表現された条件 (省略可,PAPI可)
    output-true: "§cWHITELIST-ON"  # 条件に合うときの出力  (省略可,PAPI可)
    output-false: "§aWHITELIST-OFF"  # 条件に合わないときの出力  (省略可,PAPI可)

# 値同士を繋ぐ文字列
values-join: "\0"
```

- `pattern` を省略した場合は、常に `output-true` の値を出力します
- `output-true` または `output-false` を省略すると、常に `input` で得られる値を出力します

# HomeMapWidget

デバイスの現在地から保存した自宅までのルートを示す静的な地図を表示する Android ウィジェットです。

このプロジェクトでは Google Static Maps API と WorkManager を利用し、ウィジェットの画像を定期的に更新します。

## ビルド方法
1. `local.properties` に `MAPS_API_KEY=YOUR_KEY` の形式で Google Maps API キーを記述します。
2. Gradle でビルドします。

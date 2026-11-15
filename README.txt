Recruits Scorched Pose (NeoForge 1.21.1) - client-side addon

ไฟล์ในโปรเจกต์:
  build.gradle, settings.gradle, gradle.properties
  .github/workflows/build.yml                         (ให้ GitHub build ให้เอง)
  src/main/resources/META-INF/neoforge.mods.toml
  src/main/resources/rsp.mixins.json
  src/main/resources/pack.mcmeta
  src/main/java/com/example/rsp/mixin/RecruitPoseMixin.java          (ท่าหน้าไม้ให้ปืน scguns ทั้งหมด)
  src/main/java/com/example/rsp/mixin/RecruitGatlingPoseMixin.java   (ท่าแกทลิ่ง 10 กระบอก + ท่าซอมบี้ whizzbanger)

Build: push ขึ้น GitHub แล้วดูแท็บ Actions -> Artifacts
       หรือ gradle build ในเครื่อง (JDK 21) -> build/libs/*.jar
ยังไม่ได้ compile/ทดสอบในเกม

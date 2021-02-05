FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/cart-1.0.jar /
CMD ["java", "-jar", "cart-1.0.jar"]

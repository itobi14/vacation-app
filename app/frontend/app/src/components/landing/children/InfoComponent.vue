<template>

  <div class="info-content">

    <h1 class="title">How does it work?</h1>

<!--    <div class="container-wrapper" ref="containerWrapper">-->

<!--      <div class="progress-indicator"></div>-->

<!--      <div class="container">-->

<!--      </div>-->

<!--      <div class="progress-indicator"></div>-->

<!--      <div class="container">-->

<!--      </div>-->

<!--      <div class="progress-indicator"></div>-->

<!--      <div class="container">-->

<!--      </div>-->

<!--    </div>-->

  </div>

</template>

<script>

export default {
  name: "InfoComponent",

}

</script>

<style scoped>

.info-content {
  width: 85svw;
  height: auto;
  padding: 5rem;
}

.title {
  font-size: 30px;
  font-weight: 700;
  color: var(--color-black);
  margin-bottom: 3rem;
}

.container-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.progress-indicator {
  height: 10px;
  width: 75px;
  border-radius: 50px;
  background: #e5e5e5;
}

.container {
  height: 500px;
  width: 300px;
  border-radius: 10px;
  box-shadow: rgba(149, 157, 165, 0.2) 0 8px 24px;
  background: var(--color-white);
}

</style>
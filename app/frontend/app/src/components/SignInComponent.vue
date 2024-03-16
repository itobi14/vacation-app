<template>

  <div class="content">

    <div class="loginContainer">

      <form v-on:submit.prevent="onSignIn">

        <h1 class="title">Login</h1>

        <div class="inputContainer">
          <div class="inputWrapper">
            <input type="email" name="email" v-model.trim="accountEmail" v-on:keydown.enter="$event.preventDefault()" required>
            <label for="email">Email</label>
          </div>

          <div class="inputWrapper">
            <input type="password" name="password" v-model="accountPassword" required>
            <label for="password">Password</label>
          </div>
        </div>

        <div class="errorMessageWrapper" v-show="errorMessage">
          <p class="errorMessage"> {{ errorMessage }} </p>
          <span class="material-symbols-outlined report-icon">report</span>
        </div>

        <button class="loginButton">Login</button>

        <div class="wrapper">
          <p class="info-text">Don't have an account yet? Click
            <router-link to="/sign-up">here</router-link>
            to sign up</p>
        </div>

      </form>

    </div>

  </div>

</template>

<script>
export default {

  name: "SignInComponent",
  inject: ['sessionService'],

  data() {
    return {
      accountEmail: null,
      accountPassword: null,
      errorMessage: null,
    }
  },

  methods: {

    async onSignIn() {
      this.errorMessage = null;
      let account = await this.sessionService.asyncSignIn(this.accountEmail, this.accountPassword);
      if (account === null) {
        this.errorMessage = "Could not authenticate with provided credentials. Please try again";
      } else {
        this.$router.push({ path: '/' })
      }
    },

  },

}

</script>

<style scoped>

.content {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #aaa;
  height: 92svh;
  width: 100svw;
}

.content::before {
  content: "";
  background-image: url("../assets/background.jpg");
  background-size: cover;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  opacity: 0.5;
}

.loginContainer {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

form {
  display: flex;
  justify-content: center;
  flex-direction: column;
  height: auto;
  width: 500px;
  padding: 3rem 5rem;
  border-radius: 35px;
  background: var(--white);
}

.title {
  position: relative;
  font-size: 35px;
  font-weight: 700;
  width: 35%;
  margin-bottom: 4rem;
  color: var(--black);
}

.title::before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 0;
  width: 15px;
  height: 8px;
  background: #5daab5;
}

form .inputContainer {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

form .inputWrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 4em;
  border-radius: 5px;
}

form .input-value + * {
  clear: both;
}

form input {
  position: absolute;
  height: 100%;
  width: 100%;
  padding: 1.5em 1em 0 1em;
  border: none;
  outline: none;
  background: #f5f5f5;
  border-radius: 3px;
  font-size: 14px;
  font-weight: 500;
  color: #222;
  z-index: 1;
}

form label {
  position: absolute;
  font-size: 0.75em;
  font-weight: 600;
  color: #999;
  text-transform: uppercase;
  width: 80%;
  z-index: 2;
  pointer-events: none;
  animation: labelIn;
  animation-duration: 0.3s;
  animation-direction: reverse;
  animation-fill-mode: forwards;
}

form input:focus {
  outline: 2px solid #222;
  background: none;
}

form input:focus + label, form input:valid + label {
  animation: labelOut;
  animation-duration: 0.2s;
  animation-direction: normal;
  animation-fill-mode: forwards;
}

@keyframes labelIn {

  0% {
    opacity: 1;
    transform: translateY(0);
  }

  100% {
    opacity: 0;
    transform: translateY(-35%);
  }

}

@keyframes labelOut {

  0% {
    font-size: 10px;
    top: 1em;
    width: 90%;
    transform: translateY(25%);
    opacity: 0;
  }

  100% {
    font-size: 10px;
    top: 1em;
    width: 92%;
    transform: translateY(0);
    opacity: 1;
  }

}

.loginButton {
  padding: 1em 0.5em;
  border-radius: 5px;
  text-transform: uppercase;
  font-size: 1em;
  font-weight: 500;
  background: #5daab5;
  outline: none;
  border: none;
  color: var(--white);
  margin-top: 2rem;
  cursor: pointer;
  transition: 0.2s ease-in-out;
}

.loginButton:hover {

}

.errorMessageWrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  border-radius: 5px;
  border: 2px solid red;
  padding: 0.5rem 1rem;
}

.errorMessage {
  font-size: 12px;
  font-weight: 500;
}

.wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 2rem;
}

.info-text {
  font-size: 12px;
  font-weight: 400;
}


.material-symbols-outlined.report-icon {
  color: red;
  font-variation-settings:
       'FILL' 0,
       'wght' 400,
       'GRAD' 0,
       'opsz' 24
}

</style>
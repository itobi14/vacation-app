<template>

  <div class="content">

    <div class="loginContainer">

      <form v-on:submit.prevent="onSignUp">

        <h1 class="title">Sign Up</h1>

        <div class="inputContainer">

          <div class="inputWrapper">
            <input type="text" name="firstName" v-model.trim="firstName" v-on:keydown.enter="$event.preventDefault()" required>
            <label for="firstName">First name</label>
          </div>

          <div class="inputWrapper">
            <input type="text" name="lastName" v-model="lastName" required>
            <label for="password">Last name</label>
          </div>

          <div class="inputWrapper">
            <input type="email" name="email" v-model="email" required>
            <label for="email">E-mail</label>
          </div>

          <div class="inputWrapper">
            <input type="password" name="password" v-model="password" required>
            <label for="password">Password</label>
          </div>

          <div class="inputWrapper">
            <input type="password" name="confirmPassword" v-model="confirmPassword" required>
            <label for="confirmPassword">Confirm Password</label>
          </div>

          <div class="errorMessageWrapper" v-show="errorMessage">
            <p class="errorMessage"> {{ errorMessage }} </p>
            <span class="material-symbols-outlined report-icon">report</span>
          </div>

        </div>

        <button class="loginButton">Sign Up</button>

        <div class="wrapper">
          <p class="info-text">If you already have an account, click
            <router-link to="/sign-in">here</router-link>
            to login</p>
        </div>

      </form>

    </div>

  </div>

</template>

<script>
export default {

  name: "SignUpComponent",
  inject: ['accountsService', 'sessionService'],

  data() {
    return {
      firstName: null,
      lastName: null,
      email: null,
      password: null,
      confirmPassword: null,
      errorMessage: null,
    }
  },

  methods: {

    async onSignUp() {

      if (!this.passwordsAreEqual) {
        this.errorMessage = "Passwords do not match!"
      } else {

        this.errorMessage = null;

        try {

          let newAccount = await this.accountsService.createAccount(this.firstName, this.lastName, this.email, this.password);
          console.log("Account created successfully: ", newAccount);

          if (newAccount) {
            let account = await this.sessionService.asyncSignIn(this.email, this.password);
            if (account) {
              this.$router.push({ name: 'HOME' })
            }
          }

        } catch (error) {
          console.error("Error creating account: ", error);
        }

      }
    },

  },

  computed: {
    passwordsAreEqual() {
      return this.password === this.confirmPassword;
    }
  },

}

</script>

<style scoped>

.content {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--color-darkgrey);
  height: auto;
  width: 100svw;
  padding: 2rem 0;
}

.content::before {
  content: "";
  background-image: url("../../assets/background.jpg");
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
  background: var(--color-white);
}

.title {
  position: relative;
  font-size: 1.8em;
  font-weight: 700;
  width: 40%;
  margin-bottom: 2rem;
  color: var(--color-black);
}

.title::before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 0;
  width: 15px;
  height: 8px;
  background: var(--color-primary);
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
  height: 3.5em;
  border-radius: 5px;
}

form .input-value + * {
  clear: both;
}

form input {
  position: absolute;
  height: 100%;
  width: 100%;
  padding: 1.5em 1.2em 0 1.2em;
  border: none;
  outline: none;
  background: var(--color-lightgrey);
  border-radius: 3px;
  font-size: .75em;
  font-weight: 500;
  color: var(--color-black);
  z-index: 1;
}

form label {
  position: absolute;
  font-size: .6em;
  font-weight: 500;
  color: var(--color-darkgrey);
  text-transform: uppercase;
  width: 85%;
  z-index: 2;
  pointer-events: none;
  animation: labelIn;
  animation-duration: 0.3s;
  animation-direction: reverse;
  animation-fill-mode: forwards;
}

form input:focus {
  outline: 2px solid var(--color-black);
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
    font-size: .5em;
    top: 1em;
    width: 90%;
    transform: translateY(25%);
    opacity: 0;
  }

  100% {
    font-size: .5em;
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
  background: var(--color-primary);
  outline: none;
  border: none;
  color: var(--color-white);
  opacity: 0.8;
  margin-top: 2rem;
  cursor: pointer;
  transition: 0.2s ease-in-out;
}

.loginButton:hover {
  opacity: 1;
}

.errorMessageWrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 300px;
  border-radius: 5px;
  border: 2px solid red;
  padding: 0.5rem 1rem;
}

.errorMessage {
  font-size: .8em;
  font-weight: 400;
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
  font-size: .8em;
  font-weight: 500;
}

.wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 2rem;
}

.info-text {
  font-size: .75em;
  font-weight: 400;
  color: var(--color-black)
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
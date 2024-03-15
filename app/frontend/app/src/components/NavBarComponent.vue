<template>

  <nav class="navBar">

    <div class="navBarContent">

      <router-link class="logoContainer" to="/">
        <img src="../assets/logo.svg" alt="Logo" class="logo">
      </router-link>

      <div class="navItemsWrapper">

        <div class="navItemsContainer">
          <router-link class="navItem" :to="{ path: '/accommodations' }" exact :class="{ 'isSelected': /^\/accommodations/.test($route.path) }">Accommodations</router-link>
          <router-link v-if="!isAuthenticated" class="navItem" to="/sign-in" exact :class="{ 'isSelected': $route.path === '/sign-in' }">Login</router-link>
        </div>

        <div v-if="isAuthenticated" class="profileInfoContainer">
          <div class="usernameContainer" @click="toggleDropdown">
            <span class="material-symbols-outlined face">face</span>
            <div class="username">{{ userName }}</div>

            <!-- Dropdown menu -->
            <div class="dropdownMenuContainer open" v-if="isDropdownOpen">
              <div class="dropdownProfileContainer">
                <div class="dropdownProfilePic"></div>
                <div class="dropdownProfileName">{{ userName }}</div>
              </div>
              <ul class="dropdownMenu">
                <li class="dropdownMenuItem">
                  <span class="material-symbols-outlined">person</span>
                  <span class="dropdownMenuItemText">Profile</span>
                </li>
                <li class="dropdownMenuItem">
                  <span class="material-symbols-outlined">settings_applications</span>
                  <span class="dropdownMenuItemText">Settings</span>
                </li>
                <li class="dropdownMenuItem" @click="onSignOut">
                  <span class="material-symbols-outlined">logout</span>
                  <span class="dropdownMenuItemText">Logout</span>
                </li>
              </ul>
            </div>

          </div>
          <div class="profilePic">{{ profilePic }}</div>
        </div>

      </div>

    </div>

  </nav>

</template>

<script>

export default {
  name: "NavBarComponent",
  inject: ['sessionService'],

  data() {
    return {
      isDropdownOpen: false,
      profilePic: null,
    }
  },

  methods: {

    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },

    onSignOut() {
      this.sessionService.signOut();
      this.$router.push({ path: '/' });
    },

  },

  computed: {

    isAuthenticated() {
      // console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },

    userName() {
      return this.sessionService.currentAccount.name;
    },

  },

}

</script>

<style scoped>

/* NAVBAR ------------------------------------------------------------------------ */

.navBar {
  display: flex;
  justify-content: center;
  height: 8svh;
  z-index: 99;
  background: #fff;
  border-bottom: 1px solid #eee;
  transition: height 0.5s ease;
}

.navBarContent {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 85svw;
  padding: 1rem 0;
}

.logoContainer {
  height: 50px;
  width: 50px;
  transition: all 0.3s ease-out;

  .logo {
    height: 100%;
    width: 100%;
  }

}

.navItemsWrapper {
  display: flex;
}

.navItemsContainer {
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.3s ease-out;
}

.navItem {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.4rem 1rem;
  color: var(--black);
  text-decoration: none;
  font-size: 1em;
  font-weight: 500;
  cursor: pointer;
  transition: 0.1s ease-out;
}

.isSelected {
  //background: #b43e3c;
}

.isSelected::before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 0;
  height: 7px;
  width: 7px;
  background: var(--black);
}

/* PROFILE NAVBAR ------------------------------------------------------------------ */

.profileInfoContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin-left: 2rem;
}

.usernameContainer {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 1rem;
  height: 100%;
  width: auto;
  max-width: 200px;
  padding: 0.5rem 2rem 0.5rem 1rem;
  outline: none;
  background: #fff;
  border-radius: 5px;
  cursor: pointer;
}

.usernameContainer:hover {
  outline: 3px solid var(--black);
}

.username {
  width: 100%;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  font-size: 0.8em;
  font-weight: 600;
  color: #222;
}

.profilePic {
  height: 50px;
  width: 50px;
  border-radius: 50%;
  background: #222;
  transition: all 0.3s ease-out;
}

/* DROPDOWN MENU ------------------------------------------------------------------ */

.dropdownMenuContainer {
  position: absolute;
  top: 125%;
  right: 0;
  width: 250px;
  background: #fff;
  border: 2px solid #e5e5e5;
  padding: 1rem;
  border-radius: 5px;
  opacity: 1;
  transform: translateY(-10px);
  z-index: 999;
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.dropdownMenuContainer.open {
  opacity: 1;
  transform: translateY(0);
}

.dropdownProfileContainer {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #e5e5e5;
}

.dropdownProfilePic {
  height: 50px;
  width: 50px;
  background: #222222;
  border-radius: 50%;
}

.dropdownProfileName {
  font-size: 1.2em;
  font-weight: 500;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  max-width: 65%;
  color: #222;
}

.dropdownMenu {
  margin-top: 1rem;
  list-style: none;
}

.dropdownMenuItem {
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 1rem;
  padding: 1rem;
  background: none;
  border-radius: 5px;
}

.dropdownMenuItem:hover {
  background: #f5f5f5;
}

.dropdownMenuItemText {
  font-size: 1rem;
  font-weight: 500;
}

/* ICONS ------------------------------------------------------------------------ */

.material-symbols-outlined.face {
  color: #222;
  font-variation-settings:
      'FILL' 0,
      'wght' 400,
      'GRAD' 0,
      'opsz' 40
}

.material-symbols-outlined.face.onScroll {
  display: none;
}

.material-symbols-outlined.dropdown {
  color: #222;
  font-variation-settings:
      'FILL' 0,
      'wght' 400,
      'GRAD' 0,
      'opsz' 40
}

</style>
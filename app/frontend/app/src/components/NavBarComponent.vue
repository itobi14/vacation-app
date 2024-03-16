<template>

  <nav class="nav-bar">

    <div class="nav-bar-content">

      <router-link class="logo-container" to="/">
        <img src="../assets/logo.svg" alt="Logo" class="logo">
      </router-link>

      <div class="nav-items-wrapper">

        <div class="nav-items-container">
          <router-link class="nav-item" :to="{ name: 'ACCOMMODATIONS' }" exact :class="{ 'isSelected': isAccommodationsRoute }">Accommodations</router-link>
          <router-link class="nav-item" :to="{ name: 'ABOUT-US' }" exact :class="{ 'isSelected': isAboutUsRoute }">About Us</router-link>
          <router-link v-if="!isAuthenticated" class="nav-item" :to="{ name: 'SIGN-IN' }" exact :class="{ 'isSelected': isSignInOrSignUpRoute }">Login</router-link>

          <div v-if="isAuthenticated" @click="toggleDropdown" class="profile-container">
            <img :src="this.profileImgUrl" class="profile-img" alt="Profile Image">

            <!-- Dropdown menu -->
            <div class="dropdown-menu-container open" v-if="isDropdownOpen">
              <div class="dropdown-profile-container">
                <div class="profile-container">
                  <img :src="this.profileImgUrl" class="profile-img" alt="Profile Image">
                </div>
                <div class="user-name">{{ userName }}</div>
              </div>
              <div class="dropdown-menu">
                <router-link :to=" { name: 'PROFILE' }" class="dropdown-menu-item">
                  <span class="material-symbols-outlined">person</span>
                  <p class="dropdown-menu-item-text">Profile</p>
                </router-link>
                <router-link :to=" { name: 'HOME' }" class="dropdown-menu-item" @click="onSignOut">
                  <span class="material-symbols-outlined">logout</span>
                  <p class="dropdown-menu-item-text">Logout</p>
                </router-link>
              </div>
            </div>
          </div>

        </div>

      </div>
    </div>

  </nav>

</template>

<script>

export default {
  name: "NavBarComponent",
  inject: ['sessionService', 'accountsService'],

  data() {
    return {
      isDropdownOpen: false,
      defaultProfileImgUrl: 'static/placeholder.svg',
      userProfileImgUrl: 'static/profile-img.jpg',
    }
  },

  methods: {

    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },

    onSignOut() {
      this.sessionService.signOut();
      this.$router.push({ name: 'HOME' });
    },

  },

  computed: {

    isAuthenticated() {
      // console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },

    userName() {
      return this.sessionService.currentAccount.firstName + " " + this.sessionService.currentAccount.lastName;
    },

    profileImgUrl() {
      return this.isAuthenticated ? this.userProfileImgUrl : this.defaultProfileImgUrl;
    },

    /* ROUTES ---------------------------------------------------------------------------------------------------- */

    isAccommodationsRoute() {
      return /^\/accommodations/.test(this.$route.path);
    },

    isAboutUsRoute() {
      return this.$route.path === '/about-us';
    },

    isSignInOrSignUpRoute() {
      return this.$route.path === '/sign-in' || this.$route.path === '/sign-up';
    },

  },

}

</script>

<style scoped>

/* NAVBAR ------------------------------------------------------------------------ */

.nav-bar {
  display: flex;
  justify-content: center;
  height: auto;
  z-index: 99;
  background: #fff;
  border-bottom: 1px solid #eee;
  transition: height 0.5s ease;
}

.nav-bar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 85svw;
  padding: 1rem 0;
}

.logo-container {
  height: 50px;
  width: 50px;
  transition: all 0.3s ease-out;

  .logo {
    height: 100%;
    width: 100%;
  }

}

.nav-items-wrapper {
  display: flex;
}

.nav-items-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.3s ease-out;
}

.nav-item {
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

.profile-container {
  position: relative;
  height: 50px;
  width: 50px;
  margin-left: 1rem;
  cursor: pointer;
  transition: all 0.3s ease-out;
}

.profile-img {
  height: 100%;
  width: 100%;
  border-radius: 50%;
  object-fit: cover;
}

/* DROPDOWN MENU ------------------------------------------------------------------ */

.dropdown-menu-container {
  position: absolute;
  top: 150%;
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

.dropdown-menu-container.open {
  opacity: 1;
  transform: translateY(0);
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--black);
}

.dropdown-profile-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #e5e5e5;
}

.profile-img {
  height: 50px;
  width: 50px;
  background: #222222;
  border-radius: 50%;
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  max-width: 65%;
  color: #222;
}

.dropdown-menu {
  margin-top: 1rem;
  list-style: none;
}

.dropdown-menu-item {
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 1rem;
  padding: 1rem;
  background: none;
  border-radius: 5px;
  text-decoration: none;
}

.dropdown-menu-item:hover {
  background: #f5f5f5;
}

.dropdown-menu-item-text {
  font-size: 1rem;
  font-weight: 500;
  color: var(--black);
}

/* ICONS ------------------------------------------------------------------------ */

.material-symbols-outlined {
  color: var(--black);
  transform: scale(1.1);
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 0
}

</style>
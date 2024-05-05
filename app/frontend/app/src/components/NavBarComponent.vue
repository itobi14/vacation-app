<template>

<!--  <nav :class="{ 'nav-bar': !isScrolled, 'nav-bar-scrolled': isScrolled }">-->
  <nav class="nav-bar">

    <div class="nav-bar-content">

      <router-link class="logo-container" to="/">
        <img src="../assets/logo.svg" alt="Logo" class="logo">
      </router-link>

      <div class="nav-items-wrapper">
        <router-link
            class="nav-item"
            :to="{ name: 'ACCOMMODATIONS' }"
            :class="{ 'isSelected': isAccommodationsRoute }">Accommodations
        </router-link>
        <router-link
            class="nav-item"
            :to="{ name: 'ABOUT-US' }"
            :class="{ 'isSelected': isAboutUsRoute }">About Us
        </router-link>
        <router-link
            v-if="!isAuthenticated"
            class="nav-item" :to="{ name: 'SIGN-IN' }"
            :class="{ 'isSelected': isSignInOrSignUpRoute }">Login
        </router-link>

        <div v-if="isAuthenticated" @click="toggleDropdown" class="profile-container">
          <img :src="getImgUrl(profileImg)" class="profile-img" alt="Profile Image">

          <!-- Dropdown menu -->
          <div class="dropdown-menu-container open" v-if="isDropdownOpen">
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

  </nav>

</template>

<script>

export default {
  name: "NavBarComponent",
  inject: ['sessionService', 'accountsService'],

  data() {
    return {
      isDropdownOpen: false,
      isScrolled: false,
    }
  },

  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },

  beforeUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  },

  methods: {

    handleScroll() {
      if (window.scrollY > 5) {
        if (!this.isScrolled) {
          console.log("Now scrolling past 5px");
          this.isScrolled = true;
        }
      } else {
        if (this.isScrolled) {
          console.log("Scrolled back above 5px");
          this.isScrolled = false;
        }
      }
    },

    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },

    onSignOut() {
      this.sessionService.signOut();
      this.$router.push({ name: 'HOME' });
    },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/profile-pic-placeholder.jpeg');
      }
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

    profileImg() {
      return this.sessionService._currentAccount.profileImg;
    },

    /* ROUTES -------------------------------------------------------------------------------------------------- */

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
  z-index: 99;
  height: 10svh;
  width: 100%;
  backdrop-filter: blur(0);
  border-bottom: 1px solid var(--color-primary-lighter);
  transition: .2s ease-in-out;
}

/*
.nav-bar-scrolled {
  position: fixed;
  top: 0;
  right: 0;
  display: flex;
  justify-content: center;
  z-index: 99;
  height: 10svh;
  width: 100%;
  backdrop-filter: blur(5px);
  border-bottom: 1px solid var(--color-primary-lighter);
  transition: .2s ease-in-out;
}
 */

.nav-bar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 80svw;
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
  color: var(--color-black);
  text-decoration: none;
  font-size: 1em;
  font-weight: 500;
  opacity: .5;
  transition: 0.1s ease-out;
  cursor: pointer;
}

.nav-item:not(.isSelected):hover {
  opacity: 1;
}

.isSelected {
  opacity: 1;
}

/* PROFILE NAVBAR ------------------------------------------------------------------ */

.profile-container {
  position: relative;
  height: 50px;
  width: 50px;
  margin-left: 1rem;
  transition: all 0.3s ease-out;
  cursor: pointer;
}

.profile-img {
  height: 100%;
  width: 100%;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
}

/* DROPDOWN MENU ------------------------------------------------------------------ */

.dropdown-menu-container {
  position: absolute;
  top: 125%;
  right: 0;
  height: auto;
  width: auto;
  background: var(--color-white);
  border: 2px solid var(--color-primary-lighter);
  padding: 1rem;
  border-radius: 5px;
  opacity: 1;
  transform: translateY(-10px);
  z-index: 999;
  transition: opacity 0.3s ease, transform 0.3s ease;
  cursor: pointer;
}

.dropdown-menu-container.open {
  opacity: 1;
  transform: translateY(0);
}

.profile-img {
  height: 50px;
  width: 50px;
  border-radius: 50%;
  cursor: auto;
}

.dropdown-menu {
  list-style: none;
}

.dropdown-menu-item {
  display: flex;
  align-items: center;
  width: 150px;
  gap: 1rem;
  padding: 1rem;
  background: none;
  border-radius: 5px;
  text-decoration: none;
}

.dropdown-menu-item:hover {
  background: var(--color-lightgrey);
}

.dropdown-menu-item-text {
  font-size: 14px;
  font-weight: 500;
  color: var(--color-black);
}

/* ICONS ------------------------------------------------------------------------ */

.material-symbols-outlined {
  color: var(--color-black);
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 0
}

/* SCREENS - LAPTOP ------------------------------------------------------------- */

@media only screen and (min-width: 1400px) {

  .nav-item {
    font-size: .8em;
  }

  .logo-container {
    height: 40px;
    width: 40px;
  }

  .profile-container {
    height: 40px;
    width: 40px;
  }

  .profile-img {
    height: 100%;
    width: 100%;
  }

}

</style>
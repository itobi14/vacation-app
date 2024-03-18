<template>

  <div class="content">

    <div class="profile-container">
      <img :src="getImgUrl(profileImg)" class="profile-img" alt="Profile Image">
    </div>

    <div class="favorites-container">
      <h2 class="section-title">Favorites</h2>
      <div class="favorites-wrapper">
        <div v-for="accommodation in this.favorites"
             :key="accommodation.id"
             class="accommodation"
             @click="goToDetailView(accommodation.id)">
          <img :src="getImgUrl(accommodation.imgUrl)"
               class="accommodation-img"
               alt="Accommodation Image">
          <h3 class="accommodation-name">{{ accommodation.name }}</h3>
          <p class="country-city">{{ accommodation.country }}, &nbsp; {{ accommodation.city}} </p>
        </div>
      </div>
    </div>

<!--    <div class="booking-history-container">-->
<!--      <h2 class="section-title">Booking history</h2>-->
<!--      <div v-for="accommodation in this.bookingHistory"-->
<!--           :key="accommodation.id"-->
<!--           class="item">-->
<!--        <img :src="getImgUrl(accommodation.imgUrl)"-->
<!--             class="accommodation-img"-->
<!--             alt="Accommodation Image">-->
<!--        <h3>{{ accommodation.name }}</h3>-->
<!--        <p>{{ accommodation.description }}</p>-->
<!--      </div>-->
<!--    </div>-->

  </div>

</template>

<script>
export default {
  name: "ProfileComponent",
  inject: ['sessionService', 'accountsService'],

  data() {
    return {
      id: this.sessionService.currentAccount.id,
      userName: null,
      profileImg: null,
      favorites: [],
      bookingHistory: [],
    }
  },

  async created() {
    await this.fetchAccount();
    await this.fetchFavorites();
    // await this.fetchBookingHistory();
  },

  methods: {

    async fetchAccount() {
      const account = await this.accountsService.findById(this.id);
      if (account) {
        this.userName = account.fullName;
        this.profileImg = account.profileImg;
      }
    },

    async fetchFavorites() {
      const favorites = await this.accountsService.findFavorites(this.id);
      if (favorites) {
        this.favorites = favorites
      }
    },

    // async fetchBookingHistory() {
    //   const bookingHistory = await this.accountsService.findBookingHistory(this.id);
    //   if (bookingHistory) {
    //     this.bookingHistory = bookingHistory
    //   }
    // },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/placeholder.svg');
      }
    },

    goToDetailView(id) {
      this.$router.push('/accommodations/' + id)
    },

  },

  computed: {

  },

}
</script>

<style scoped>

.content {
  height: 100svh;
  width: 85svw;
  background: #f5f5f5;
}

.profile-container {
  height: 200px;
  width: 200px;
}

.profile-img {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.favorites-container {
  padding: 1rem;
  width: 750px;
  overflow-x: scroll;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
}

.favorites-wrapper {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.accommodation {
  background: var(--white);
  border-radius: 5px;
  border: 1px solid #e5e5e5;
  padding: 10px;
  cursor: pointer;
}

.accommodation-img {
  height: 85px;
  width: 100%;
  object-fit: cover;
}

.accommodation-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--black);
}

.country-city {
  font-size: 12px;
  font-weight: 400;
  color: #555
}

</style>
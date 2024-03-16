<template>

  <div class="content">

    <div class="profile-container">
      <img :src="getImgUrl(profileImg)" class="profile-img" alt="Profile Image">
    </div>

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
    }
  },

  async created() {
    await this.fetchAccount();
  },

  methods: {

    async fetchAccount() {
      const account = await this.accountsService.findById(this.id);
      if (account) {
        this.userName = account.fullName;
        this.profileImg = account.profileImg;
      }
    },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/placeholder.svg');
      }
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

</style>
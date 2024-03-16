import { createRouter, createWebHashHistory } from 'vue-router'
import HeroComponent from "@/components/hero/HeroComponent.vue";
import SignInComponent from "@/components/SignInComponent.vue";
import SignUpComponent from "@/components/SignUpComponent.vue";
import AccommodationsComponent from "@/components/AccommodationsComponent.vue";
import AccommodationDetailComponent from "@/components/AccommodationDetailComponent.vue";
import AboutUsComponent from "@/components/AboutUsComponent.vue";
import ProfileComponent from "@/components/ProfileComponent.vue";
import UnauthorizedComponent from "@/components/UnauthorizedComponent.vue";

const routes = [
    { path: '/', name: 'HOME', component: HeroComponent },
    { path: '/sign-in', name: 'SIGN-IN', component: SignInComponent, props: true },
    { path: '/sign-up', name: 'SIGN-UP', component: SignUpComponent, props: true },
    { path: '/accommodations', name: 'ACCOMMODATIONS', component: AccommodationsComponent },
    { path: '/accommodations/:id', name: 'ACCOMMODATION-DETAIL', component: AccommodationDetailComponent },
    { path: '/about-us', name: 'ABOUT-US', component: AboutUsComponent },
    { path: '/profile', name: 'PROFILE', component: ProfileComponent },
    { path: '/unauthorised', name: 'UNAUTHORIZED', redirect: '/', component: UnauthorizedComponent }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
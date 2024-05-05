import { createRouter, createWebHashHistory } from 'vue-router'
import HeroComponent from "@/components/landing/HeroComponent.vue";
import SignInComponent from "@/components/login/SignInComponent.vue";
import SignUpComponent from "@/components/login/SignUpComponent.vue";
import AccommodationsComponent from "@/components/accommodation-overview/AccommodationsComponent.vue";
import AccommodationDetailComponent from "@/components/accommodation-detail/AccommodationDetailComponent.vue";
import AboutUsComponent from "@/components/aboutus/AboutUsComponent.vue";
import ProfileComponent from "@/components/profile/ProfileComponent.vue";
import UnauthorizedComponent from "@/components/other/UnauthorizedComponent.vue";
import NotFoundComponent from "@/components/other/NotFoundComponent.vue";

const routes = [
    { path: '/', name: 'HOME', component: HeroComponent },
    { path: '/sign-in', name: 'SIGN-IN', component: SignInComponent, props: true },
    { path: '/sign-up', name: 'SIGN-UP', component: SignUpComponent, props: true },
    { path: '/accommodations', name: 'ACCOMMODATIONS', component: AccommodationsComponent },
    { path: '/accommodations/:id', name: 'ACCOMMODATION-DETAIL', component: AccommodationDetailComponent },
    { path: '/about-us', name: 'ABOUT-US', component: AboutUsComponent },
    { path: '/profile', name: 'PROFILE', component: ProfileComponent },
    { path: '/unauthorised', name: 'UNAUTHORIZED', component: UnauthorizedComponent },
    { path: '/:catchAll(.*)*', name: 'NOT-FOUND', component: NotFoundComponent }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
    linkActiveClass: 'active',
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition;
        }
        return {left: 0, top: 0,};
    },
})

export default router
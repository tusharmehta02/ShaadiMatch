package com.shaadi.assignment.di

import com.shaadi.assignment.di.module.NetworkModule
import com.shaadi.assignment.di.module.StorageModule
import com.shaadi.assignment.di.module.ViewModelModule
import com.shaadi.assignment.ui.fragment.DashboardFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(fragment: DashboardFragment)
}
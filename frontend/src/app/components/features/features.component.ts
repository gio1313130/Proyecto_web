import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-features',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './features.component.html',
  styleUrl: './features.component.css'
})
export class FeaturesComponent {
  activeTab: 'alumnos' | 'admin' = 'alumnos';

  setTab(tab: 'alumnos' | 'admin') {
    this.activeTab = tab;
  }
}

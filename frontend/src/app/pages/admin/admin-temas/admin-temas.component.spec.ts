import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTemasComponent } from './admin-temas.component';

describe('AdminTemasComponent', () => {
  let component: AdminTemasComponent;
  let fixture: ComponentFixture<AdminTemasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTemasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTemasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

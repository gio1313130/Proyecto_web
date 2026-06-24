import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCuestionariosComponent } from './admin-cuestionarios.component';

describe('AdminCuestionariosComponent', () => {
  let component: AdminCuestionariosComponent;
  let fixture: ComponentFixture<AdminCuestionariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCuestionariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCuestionariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

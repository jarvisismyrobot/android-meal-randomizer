#!/usr/bin/env python3
"""
Generate Android app icon sizes from a source image.
Android adaptive icons require multiple sizes:
- mdpi: 48x48 (1x)
- hdpi: 72x72 (1.5x)  
- xhdpi: 96x96 (2x)
- xxhdpi: 144x144 (3x)
- xxxhdpi: 192x192 (4x)
- Play Store: 512x512
"""

import os
from PIL import Image
import shutil

def generate_icons(source_path, output_dir="app/src/main/res"):
    """Generate Android icon sizes from source image."""
    
    # Android density buckets and their multipliers
    densities = {
        'mdpi': 1.0,      # 48dp = 48px
        'hdpi': 1.5,      # 48dp = 72px  
        'xhdpi': 2.0,     # 48dp = 96px
        'xxhdpi': 3.0,    # 48dp = 144px
        'xxxhdpi': 4.0,   # 48dp = 192px
    }
    
    # Base size in dp (48dp is standard launcher icon size)
    base_dp = 48
    
    # Play Store size
    play_store_size = 512
    
    # Create output directories
    os.makedirs(output_dir, exist_ok=True)
    
    # Open source image
    try:
        img = Image.open(source_path)
        print(f"Source image: {img.size}, mode: {img.mode}")
        
        # Convert to RGBA if needed
        if img.mode != 'RGBA':
            img = img.convert('RGBA')
            print("Converted to RGBA")
        
        # Generate density-specific icons
        for density, multiplier in densities.items():
            size = int(base_dp * multiplier)
            resized = img.resize((size, size), Image.Resampling.LANCZOS)
            
            # Save to mipmap directory
            mipmap_dir = os.path.join(output_dir, f"mipmap-{density}")
            os.makedirs(mipmap_dir, exist_ok=True)
            
            output_path = os.path.join(mipmap_dir, "ic_launcher.png")
            resized.save(output_path, 'PNG')
            print(f"Generated {density}: {size}x{size} -> {output_path}")
            
            # Also create round version (same image for now)
            round_path = os.path.join(mipmap_dir, "ic_launcher_round.png")
            resized.save(round_path, 'PNG')
            print(f"Generated {density} round: {round_path}")
        
        # Generate Play Store icon
        play_store_resized = img.resize((play_store_size, play_store_size), Image.Resampling.LANCZOS)
        play_store_path = os.path.join(output_dir, "ic_launcher_play_store.png")
        play_store_resized.save(play_store_path, 'PNG')
        print(f"Generated Play Store: {play_store_size}x{play_store_size} -> {play_store_path}")
        
        print("\nIcon generation complete!")
        print("Note: For adaptive icons, you'll also need to update:")
        print("1. app/src/main/res/drawable/ic_launcher_foreground.xml")
        print("2. app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml")
        print("3. app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml")
        
    except Exception as e:
        print(f"Error: {e}")
        return False
    
    return True

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) < 2:
        print("Usage: python generate_icons.py <source_image> [output_dir]")
        print("Default output_dir: app/src/main/res")
        sys.exit(1)
    
    source_image = sys.argv[1]
    output_dir = sys.argv[2] if len(sys.argv) > 2 else "app/src/main/res"
    
    if not os.path.exists(source_image):
        print(f"Error: Source image '{source_image}' not found")
        sys.exit(1)
    
    generate_icons(source_image, output_dir)